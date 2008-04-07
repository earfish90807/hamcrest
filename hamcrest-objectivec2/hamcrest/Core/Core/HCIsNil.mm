#import "HCIsNil.h"

#import "HCDescription.h"
#import "HCIsNot.h"


@implementation HCIsNil

+ (HCIsNil*) isNil
{
    return [[[HCIsNil alloc] init] autorelease];
}


- (BOOL) matches:(id)item
{
    return item == nil;
}


- (void) describeTo:(id<HCDescription>)description
{
    [description appendText:@"nil"];
}

@end


extern "C" {

id<HCMatcher> HC_nilValue()
{
    return [HCIsNil isNil];
}


id<HCMatcher> HC_notNilValue()
{
    return HC_isNot([HCIsNil isNil]);
}

}   // extern "C"
